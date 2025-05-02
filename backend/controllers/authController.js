import bcrypt from "bcrypt";
import User from "../models/User.js";
import Admin from "../models/Admin.js";
import { StatusCodes } from "http-status-codes"; 
import { generateWebToken } from "../utils/jwt.js";
import crypto from "crypto";  

export const signup = async (req, res) => {
    try {
        const { name, email, password, role, region, city } = req.body;
        const validRoles = ["Citizen", "Repair team"];
        
        if (!name || !email || !password || !role || !region || !city) {
            return res.status(StatusCodes.BAD_REQUEST).json({ message: "All fields are required" });
        }
        
        if (!validRoles.includes(role)) {
            return res.status(StatusCodes.BAD_REQUEST).json({ message: "Invalid role. Must be 'Citizen' or 'Repair team'." });
        }

        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(StatusCodes.BAD_REQUEST).json({ message: "User with this email already exists!" });
        }

        const saltRounds = 10;
        const hashedPassword = await bcrypt.hash(password, saltRounds);
        
        const newUser = new User({
            name,
            email,
            password: hashedPassword,
            imageUrl: req.file ? `/uploads/${req.file.filename}` : null, 
            role,
            region,
            city
        });

        await newUser.save();
        const token = generateWebToken(res, newUser._id, newUser.role);

        return res.status(StatusCodes.CREATED).json({ 
            message: "User registered successfully!", 
            token,
            user: {
                _id: newUser._id,
                name: newUser.name,
                email: newUser.email,
                role: newUser.role,
                imageUrl: newUser.imageUrl
            }
        });

    } catch (error) {
        console.error("Signup error:", error);
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ 
            message: "Server error", 
            error: error.message 
        });
    }
};

export const login = async (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(StatusCodes.BAD_REQUEST).json({ msg: "Please enter all required fields" });
    }

    try {
        let user = await User.findOne({ email });
        if (!user) {
            user = await Admin.findOne({ email });
        }
        if (!user) {
            return res.status(StatusCodes.UNAUTHORIZED).json({
                success: false,
                message: `No account found for email ${email}`
            });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(StatusCodes.UNAUTHORIZED).json({ msg: "Invalid credentials" });
        }

        generateWebToken(res, user._id, user.role);

        return res.status(StatusCodes.OK).json({
            msg: "User logged in successfully",
            username: user.name,
            role: user.role,
        });

    } catch (error) {
        console.error(error.message);
        return res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ msg: "Something went wrong, try again later!" });
    }
};

export const logout = async (req, res) => {
    try {
        res.clearCookie("jwt");
        return res.status(StatusCodes.OK).json({ msg: "User logged out successfully" });
    } catch (error) {
        console.error(error.message);
        return res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ msg: "Something went wrong, try again later!" });
    }
};


export const forgotPassword = async (req, res) => {
  const { email } = req.body;

  if (!email) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: "Email is required" });
  }

  const user = await User.findOne({ email });
  if (!user) {
    return res.status(StatusCodes.NOT_FOUND).json({ message: "No user found with this email" });
  }

  const resetToken = crypto.randomBytes(32).toString("hex");
  const tokenExpiresAt = Date.now() + 15 * 60 * 1000; 

  user.resetToken = resetToken;
  user.resetTokenExpiresAt = tokenExpiresAt;
  await user.save();

  const resetLink = `http://localhost:3000/reset-password/${resetToken}`;
  sendResetEmail(email, resetLink);

  return res.status(StatusCodes.OK).json({ message: "Reset link has been sent to your email" });
};

export const resetPassword = async (req, res) => {
  const { token } = req.params;
  const { newPassword } = req.body;

  if (!newPassword) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: "New password is required" });
  }

  const user = await User.findOne({
    resetToken: token,
    resetTokenExpiresAt: { $gt: Date.now() }, 
  });

  if (!user) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: "Invalid or expired token" });
  }

  const salt = await bcrypt.genSalt(10);
  user.password = await bcrypt.hash(newPassword, salt);
  user.resetToken = undefined;
  user.resetTokenExpiresAt = undefined;
  await user.save();

  return res.status(StatusCodes.OK).json({ message: "Password has been reset successfully" });
};

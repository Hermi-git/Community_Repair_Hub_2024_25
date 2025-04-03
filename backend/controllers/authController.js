import bcrypt from "bcrypt";
import User from "../models/User.js";
import { StatusCodes } from "http-status-codes"; 
import { generateWebToken } from "../utils/jwt.js";  

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
            role,
            region,
            city
        });

        await newUser.save();
        const token = generateWebToken(res, newUser._id, newUser.role);
        console.log(token);

        return res.status(StatusCodes.CREATED).json({ message: "User registered successfully!", token });

    } catch (error) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ message: "Server error", error: error.message });
    }
};



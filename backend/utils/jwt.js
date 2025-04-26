import jwt from "jsonwebtoken";
import dotenv from "dotenv"; 
dotenv.config();

export const generateWebToken = (res, id, role) => {
    const token = jwt.sign(
        { id, role },  
        process.env.JWT_SECRET,  
        { expiresIn: "1d" }
    );

    res.cookie("auth_token", token, {
        httpOnly: true,  
        secure: process.env.NODE_ENV === "production", 
        sameSite: "Strict",  
        maxAge: 24 * 60 * 60 * 1000,  
    });

    return token;  
};

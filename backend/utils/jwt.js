import jwt from "jsonwebtoken";
import dotenv from "dotenv"; 
dotenv.config();

export const generateWebToken = (res, id, role) => {
    const token = jwt.sign(
        { id, role },  
        process.env.JWT_SECRET,  
        { expiresIn: "1d" }
    );

    return token;  
};

import mongoose from "mongoose";
import dotenv from "dotenv";
dotenv.config()
export const connectToDb = async()=>{
    try {
        await mongoose.connect(process.env.MONGO_URI)
    } catch (error) {
        console.log("error while creating connection")
        process.exit(1)
    }
};
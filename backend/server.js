import app from "../backend/app.js";
import dotenv from "dotenv";
import { connectToDb } from "./config/db.js";
import fs from "fs";
import path from "path";

dotenv.config();

const createUploadsDir = () => {
  try {
    const __dirname = path.resolve();
    const uploadsDir = path.join(__dirname, "uploads");
    
    if (!fs.existsSync(uploadsDir)) {
      fs.mkdirSync(uploadsDir, { recursive: true });
      console.log("Uploads directory created successfully");
    }
  } catch (err) {
    console.error("Error creating uploads directory:", err);
    process.exit(1); 
  }
};

const startServer = async () => {
  try {
    createUploadsDir();
    await connectToDb();
    
    app.listen(process.env.PORT, () => {
      console.log(`Server is running on port ${process.env.PORT}`);
    });
  } catch (err) {
    console.error("Server startup failed:", err);
    process.exit(1);
  }
};

startServer();
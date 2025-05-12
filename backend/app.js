import express from "express";
import cookieParser from "cookie-parser";
import authRoutes from "./routes/authRoutes.js";
import citizenRoutes from "./routes/citizenRoute.js"
import cors from "cors";
import path from 'path';
import { fileURLToPath } from 'url';
import adminRoutes from "./routes/adminRoute.js";
import multer from 'multer';
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const app = express();
app.use(cors({
    origin: '*', 
    methods: ['GET', 'POST', 'PUT', 'DELETE'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: true, limit: '50mb' }));
app.use(cookieParser());
app.use('/uploads', express.static(path.join(__dirname, 'uploads'))); 
app.use("/users", authRoutes);
app.use("/citizens", citizenRoutes);
app.use("/admin", adminRoutes);
app.use((err, req, res, next) => {
    console.error('Global error handler:', {
        message: err.message,
        stack: err.stack,
        name: err.name
    });
    if (err instanceof multer.MulterError) {
        return res.status(400).json({
            success: false,
            message: `Upload error: ${err.message}`
        });
    }
    res.status(500).json({
        success: false,
        message: 'Something went wrong!',
        error: err.message,
        details: process.env.NODE_ENV === 'development' ? err.stack : undefined
    });
});


app.use((err, req, res, next) => {
    if (err instanceof multer.MulterError) {
      return res.status(400).json({ success: false, message: err.message });
    }
    if (err) {
      return res.status(500).json({ success: false, message: err.message });
    }
    next();
  });
export default app;
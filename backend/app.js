import express from "express";
import cookieParser from "cookie-parser";
import authRoutes from "./routes/authRoutes.js";
import citizenRoutes from "./routes/citizenRoute.js"
import cors from "cors";
import path from 'path';
import { fileURLToPath } from 'url';
import adminRoutes from "./routes/adminRoute.js";


const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();


app.use(cors({
    origin: '*', 
    methods: ['GET', 'POST', 'PUT', 'DELETE'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));
app.use(express.json());
app.use(cookieParser());
app.use('/uploads', express.static(path.join(__dirname, 'uploads'))); 
app.use("/users", authRoutes);
app.use("/citizens",citizenRoutes)
app.use("/admin", adminRoutes); 

export default app;

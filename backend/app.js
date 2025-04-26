import express from "express";
import cookieParser from "cookie-parser";
import authRoutes from "./routes/authRoutes.js";
import cors from "cors";
const app = express()
app.use(cookieParser());
app.use("/users", authRoutes)
app.use(cors());
app.use(express.json())
export default app;

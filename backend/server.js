import app from "../backend/app.js";
import dotenv from "dotenv";
dotenv.config()
import {connectToDb}  from "./config/db.js";
connectToDb().then(()=>{
    app.listen(process.env.PORT,()=>{
        console.log(`server is running on port`)
    })
})




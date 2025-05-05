import mongoose from "mongoose";
const userSchema = new mongoose.Schema({
    name:{
        type:String,
        required:true,
    },
    Image:{
        url:String,
      },
    email:{
        type:String,
        required:true,
        unique:true
    },
    password:{
        type:String,
        required:true
    },
    role:{
        type:String,
        enum:["Citizen","Repair team"],
        required:true
    },
    status:{
        type:String,
        enum:["approved","pending_repairteam","declined"],
        default:"pending_repairteam"
    },
    resetToken:{
        type:String
    },
    resetTokenExpiresAt:{
        type:Date
    }
})

const User = mongoose.model("user",userSchema)
export default User;    
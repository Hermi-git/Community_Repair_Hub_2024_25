import mongoose from "mongoose";
const adminSchema = new mongoose.Schema({
  name: { type: String, 
    required: true 
    },
  email: { type: String,  
    required: true
     },
  password: { type: String,
     required: true 
    }, 
  role: { type: String,
     default: "admin" 
    }, 
  resetToken:{
        type:String
    },
  resetTokenExpiresAt:{
        type:Date
    }
})
const Admin = mongoose.model("admin", adminSchema)
export default Admin;

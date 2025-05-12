import mongoose from "mongoose";
// const regionToCities = {
//     "Addis Ababa": ["Addis Ababa"],
//     "Oromia": ["Adama", "Dire Dawa", "Jimma", "Shashemene"],
//     "Amhara": ["Bahir Dar", "Gondar", "Dessie", "Debre Markos"],
//     "Tigray": ["Mekelle", "Shire", "Axum", "Adigrat"],
//     "Sidama": ["Hawassa"],
//     "Somali": ["Jigjiga", "Degehabur", "Gode"],
//     "Benishangul-Gumuz": ["Assosa", "Metekel", "Kamashi"],
//     "Gambella": ["Gambella", "Abobo", "Itang"],
//     "Afar": ["Semera", "Dubti", "Logiya"],
//     "Southern Nations, Nationalities, and Peoples' Region (SNNPR)": ["Arba Minch", "Jinka", "Wolayta Sodo"]
//   };
  
const userSchema = new mongoose.Schema({
    name:{
        type:String,
        required:true,
    },
    imageUrl:{
        type:String,
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
    Address:{
        region:{
            type:String,
        },
        city:{
            type:String,
        },
    },
    role:{
        type:String,
        enum:["Citizen","RepairTeam"],
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
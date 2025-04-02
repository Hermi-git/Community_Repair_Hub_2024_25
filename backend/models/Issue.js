import mongoose from "mongoose";

const citySpecificAreas = {
  "Addis Ababa": ["Bole", "Lideta", "Piazza", "Arat Kilo", "Megenagna", "Mexico", "Sar Bet", "Summit", "CMC"],
  "Adama": ["Dabe Laga", "Fincha’a", "Geda", "Loke", "Boku", "Bulbula"],
  "Bahir Dar": ["Kebel 03", "Kebel 14", "Shumabo", "Tana", "St. Giorgis"],
  "Mekelle": ["Hawelti", "Adi Haki", "Quiha", "Aynalem", "Enda Mariam"],
  "Hawassa": ["Tabor", "Piassa", "Dume", "Misrak", "Bahir Dar Sefer"],
  "Jigjiga": ["Ayrub", "Kebele 01", "Kebele 03", "Barwaaqo", "Gurmad"],
  "Assosa": ["Homosha", "Mandura", "Menge", "Debre Zeit", "Yaso"],
  "Gambella": ["Itang", "Abobo", "Pinyudo", "Jikawo", "Makuey"],
  "Semera": ["Jegol", "Aboker", "Sofi", "Shenkor", "Hakim", "Dubti", "Logiya", "Awash", "Gawane", "Dewe"]
};

const issueSchema = new mongoose.Schema({
    category:{
        type:String,
    },
    locations: {
          city: {
            type:String,
            required:true,
            enum:Object.keys(citySpecificAreas),
          },
          specficArea:{
            type:String,
            required:true,
            validate:{
              validator:function (area){
                return citySpecificAreas[this.locations.city]?.includes(area)
              },
              message:(props)=>`${props.value} is not valid area for the selected city.`,
            }
          }
    },
    Description:{
      type:String,
    },
    Date:{
      type:Date
    },
    Status: {
      type: String,
      enum: ["In Progress", "Resolved", "Unresolved"],
      default: "Unresolved"
    },
    Image:{
      url:String,
    },
    createdAt:{
      type:Date,
      Default:Date.now()
    },
    updatedAt:{
      type:Date,
      Default:Date.now()
    }
})      
const Issues = new mongoose.model("issues",issueSchema);
export default Issues;


        



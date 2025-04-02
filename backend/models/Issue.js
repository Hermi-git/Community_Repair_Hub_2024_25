import mongoose from "mongoose";
const issueSchema = new mongoose.Schema({
    category:{
        type:String,
    },
    locations: {
          city: {
            type:String,
            enum:[
            "Addis Ababa",
            "Adama",
            "Bahir Dar",
            "Mekelle",
            "Hawassa",
            "Jigjiga",
            "Assosa",
            "Gambella",
            "Semera"
            ]
          },
          specficArea:{
            type:String,
            enum:[
              "Bole",
              "Lideta",
              "Piazza",
              "Arat Kilo",
              "Megenagna",
              "Mexico",
              "Sar Bet",
              "Summit",
              "CMC",
              "Dabe Laga",
              "Fincha’a",
              "Geda",
              "Loke",
              "Boku",
              "Bulbula",
              "Kebel 03",
              "Kebel 14",
              "Shumabo",
              "Tana",
              "St. Giorgis",
              "Hawelti",
              "Adi Haki",
              "Quiha",
              "Aynalem",
              "Enda Mariam",
              "Tabor",
              "Piassa",
              "Dume",
              "Misrak",
              "Bahir Dar Sefer",
              "Ayrub",
              "Kebele 01",
              "Kebele 03",
              "Barwaaqo",
              "Gurmad",
              "Homosha",
              "Mandura",
              "Menge",
              "Debre Zeit",
              "Yaso",
              "Itang",
              "Abobo",
              "Pinyudo",
              "Jikawo",
              "Makuey",
              "Jegol",
              "Aboker",
              "Sofi",
              "Shenkor",
              "Hakim",
              "Dubti",
              "Logiya",
              "Awash",
              "Gawane",
              "Dewe"
            ]
          }
    },
    Description:{
      type:String,
    },
    Date:{
      type:Date
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


        



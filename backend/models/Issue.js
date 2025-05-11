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
  category: {
    type: String,
    required: true,
  },
  locations: {
    city: {
      type: String,
      required: true,
      enum: Object.keys(citySpecificAreas),
    },
    specificArea: {
      type: String,
      required: true,
      validate: {
        validator: function (area) {
          return citySpecificAreas[this.locations.city]?.includes(area);
        },
        message: (props) => `${props.value} is not a valid area for the selected city.`,
      },
    },
  },
  description: {
    type: String,
    required: true,
  },
  issueDate: {
    type: Date,
    required: true,
},
  status: {
    type: String,
    enum: ["In Progress", "Resolved", "Unresolved"],
    default: "Unresolved",
  },
  imageURL: {
    type: String,
    required: true,
  },
  createdAt: {
    type: Date,
    default: Date.now,
  },
  updatedAt: {
    type: Date,
    default: Date.now,
  },
});

// Middleware to validate city and area relationship
issueSchema.pre("validate", function (next) {
  if (!citySpecificAreas[this.locations.city]?.includes(this.locations.specificArea)) {
    this.invalidate("locations.specificArea", `${this.locations.specificArea} is not a valid area for the selected city.`);
  }
  next();
});

const Issues = mongoose.model("issues", issueSchema);
export default Issues;

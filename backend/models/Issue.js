import mongoose from "mongoose";

// Define city-specific areas mapping
const citySpecificAreas = {
    "Addis Ababa": ["Bole", "Sarbet", "Summit", "CMC", "Ayat", "Gerji", "Saris", "Megenagna", "Merkato"],
    "Dire Dawa": ["Keble 01", "Keble 02", "Keble 03", "Keble 04", "Keble 05", "Industrial Area"],
    "Bahir Dar": ["Tana", "Gish Abay", "Tis Abay", "Lake Side", "University Area", "Central Market"],
    "Hawassa": ["Lake Side", "University Area", "Industrial Zone", "Central Market", "Tabor"],
    "Mekelle": ["Ayder", "Adi Haki", "Industrial Area", "Central Market", "Semien"],
    "Jimma": ["Abay", "Bishoftu", "Central Market", "University Area", "Airport Road"],
    "Gondar": ["Fasil", "Azezo", "Central Market", "University Area", "Maraki"],
    "Adama": ["Central Market", "Industrial Zone", "University Area", "Lake Side", "Airport Road"],
    "Dessie": ["Central Market", "University Area", "Industrial Zone", "Airport Road", "Lake Side"],
    "Harar": ["Jugol", "New Town", "Industrial Zone", "University Area", "Airport Road"]
};

const issueSchema = new mongoose.Schema({
    category: {
        type: String,
        required: true,
    },
    locations: {
        city: {
            type: String,
            enum: Object.keys(citySpecificAreas),
            required: true
        },
        specificArea: {
            type: String,
            required: true
        }
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
issueSchema.pre("validate", function(next) {
    if (!citySpecificAreas[this.locations.city]?.includes(this.locations.specificArea)) {
        this.invalidate("locations.specificArea", `${this.locations.specificArea} is not a valid area for the selected city.`);
    }
    next();
});

const Issues = mongoose.model("issues", issueSchema);
export default Issues;
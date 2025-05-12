import mongoose from "mongoose";

// Define city-specific areas mapping
const citySpecificAreas = {
    "Mumbai": ["Andheri", "Bandra", "Colaba", "Dadar", "Juhu"],
    "Delhi": ["Connaught Place", "Dwarka", "Hauz Khas", "Rohini", "Saket"],
    "Bangalore": ["Indiranagar", "Koramangala", "MG Road", "Whitefield", "Electronic City"],
    "Chennai": ["Adyar", "Anna Nagar", "T Nagar", "Velachery", "Mylapore"],
    "Kolkata": ["Park Street", "Salt Lake", "New Town", "Howrah", "Dum Dum"]
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
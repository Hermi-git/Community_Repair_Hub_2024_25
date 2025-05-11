import User from "../models/User.js"
import Issues from "../models/Issue.js"


export const getIssues = async (req, res) => {
    try {
        const issues = await Issues.find().sort({ createdAt: -1 });

        return res.status(200).json({
            success: true,
            message: "All issues retrieved successfully",
            data: issues
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: "Failed to retrieve issues",
            error: error.message
        });
    }
};


export const reportIssue = async (req, res) => {
    const { imageURL, category, city, specificAddress, description, issueDate } = req.body;
    try {
        if (!category || !city) {
            return res.status(400).json({
                success: false,
                message: "Please enter the category and the city where the issue is located!",
            });
        }

        const newIssue = new Issues({
            imageURL,
            category,
            locations: {
                city,
                specificArea: specificAddress,
            },
            description,
            issueDate: new Date(issueDate), 
        });

        await newIssue.save();
        return res.status(200).json({
            success: true,
            message: "Your issue has been successfully submitted! Please note that you will be held accountable if this issue is found to be false or fabricated.",
        });
    } catch (error) {
        console.error("Error in reportIssue:", error);
        res.status(500).json({
            message: "Internal Server Error!!!",
            success: false,
            error: error.message,
        });
    }
};



export const searchByCategory = async (req, res) => {
    const { category } = req.query;
    try {
        if (!category) {
            return res.status(400).json({
                success: false,
                message: "Category is required!"
            });
        }
        const issues = await Issues.find({
            category: { $regex: category, $options: "i" } 
        }).sort({ createdAt: -1 });

        return res.status(200).json({
            success: true,
            message: "Successfully retrieved issues by category",
            data: issues
        });
    } catch (error) {
        res.status(500).json({ success: false, message: "Server Error!", error: error.message });
    }
};

export const searchByLocation = async (req, res) => {
    const { location } = req.query;
    try {
        if (!location) {
            return res.status(400).json({
                success: false,
                message: "Location is required!"
            });
        }
        const issues = await Issues.find({
            location: { $regex: location, $options: "i" }
        }).sort({ createdAt: -1 });

        return res.status(200).json({
            success: true,
            message: "Successfully filtered by location",
            data: issues
        });
    } catch (error) {
        res.status(500).json({ success: false, message: "Server Error!", error: error.message });
    }
};

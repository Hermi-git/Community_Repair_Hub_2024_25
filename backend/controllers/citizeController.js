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


// backend/controllers/citizenController.js
export const reportIssue = async (req, res) => {
    try {
        console.log('Request body:', req.body);
        console.log('Request file:', req.file);

        const { category, city, specificAddress, description, issueDate } = req.body;
        
        if (!category || !city) {
            return res.status(400).json({
                success: false,
                message: "Please enter the category and the city where the issue is located!",
            });
        }

        // Get the uploaded image path
        if (!req.file) {
            return res.status(400).json({
                success: false,
                message: "Image is required!",
            });
        }

        // Log the file information
        console.log('Uploaded file:', {
            filename: req.file.filename,
            path: req.file.path,
            mimetype: req.file.mimetype
        });

        const imageURL = `/uploads/${req.file.filename}`;

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

        const savedIssue = await newIssue.save();
        console.log('Saved issue:', savedIssue);

        return res.status(200).json({
            success: true,
            message: "Your issue has been successfully submitted!",
            data: savedIssue
        });
    } catch (error) {
        console.error("Detailed error in reportIssue:", {
            message: error.message,
            stack: error.stack,
            name: error.name
        });
        
        res.status(500).json({
            success: false,
            message: "Internal Server Error!!!",
            error: error.message,
            details: process.env.NODE_ENV === 'development' ? error.stack : undefined
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
// Get a single issue by ID
export const getIssueById = async (req, res) => {
    try {
        const { id } = req.params;
        const issue = await Issues.findById(id);

        if (!issue) {
            return res.status(404).json({
                success: false,
                message: "Issue not found"
            });
        }

        return res.status(200).json(issue); // Return the issue object directly
    } catch (error) {
        res.status(500).json({
            success: false,
            message: "Failed to fetch issue",
            error: error.message
        });
    }
};
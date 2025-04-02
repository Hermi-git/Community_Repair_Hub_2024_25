import Issues from "../models/Issue.js"
import  User from "../models/User.js"
export const TakeIssue = async (req, res) => {
    try {
        const { issueId, description, status } = req.body;
        const issue = await Issues.findById(issueId);
        if (!issue) {
            return res.status(404).json({
                success: false,
                message: "Issue Not Found!!!"
            });
        }
        issue.description = description;
        issue.status = status;
        await issue.save();

        return res.status(200).json({
            success: true,
            message: "Issue updated successfully!!!",
            updatedIssue: issue
        });
    } catch (error) {
        return res.status(500).json({
            success: false,
            message: "Server Error",
            error: error.message
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

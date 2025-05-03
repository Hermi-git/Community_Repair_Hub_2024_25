import Admin from "../models/Admin.js";
import User from "../models/User.js";


export const listUsers = async (req, res) => {
    try {
        const users = await User.find({}, "name role email createdAt status")  
                              .sort({ createdAt: -1 }); 

        if (!users || users.length === 0) {
            return res.status(404).json({ message: "No users found!" });
        }

        return res.status(200).json(users);
    } catch (error) {
        console.error("Error fetching users:", error);
        return res.status(500).json({ message: "Server error!" });
    }
};


export const approveRepairTeam = async (req, res) => {
    try {
        const { userId } = req.body;
        
        const user = await User.findById(userId);
        
        if (!user) {
            return res.status(404).json({ message: "User not found!" });
        }
        
        if (user.role !== 'pending_repairteam') {
            return res.status(400).json({ 
                message: "User is not a pending repair team member or is already approved" 
            });
        }
        
        user.role = 'repairteam';
        user.status = 'approved';
        await user.save();
        
        return res.status(200).json({
            success: true,
            message: "Repair team member approved successfully!",
            user: {
                id: user._id,
                name: user.name,
                email: user.email,
                role: user.role,
                status: user.status
            }
        });
        
    } catch (error) {
        console.error("Error approving repair team:", error);
        return res.status(500).json({ message: "Server error!" });
    }
}

export const declineRepairTeam = async (req, res) => {
    try {
        const { userId } = req.body;
        
        const user = await User.findById(userId);
        
        if (!user) {
            return res.status(404).json({ message: "User not found!" });
        }
        
        if (user.role !== 'pending_repairteam') {
            return res.status(400).json({ 
                message: "User is not a pending repair team member" 
            });
        }
        user.role = 'user';
        user.status = 'declined';
        await user.save();
        
        return res.status(200).json({
            success: true,
            message: "Repair team request declined successfully!",
            user: {
                id: user._id,
                name: user.name,
                email: user.email,
                role: user.role,
                status: user.status
            }
        });
        
    } catch (error) {
        console.error("Error declining repair team:", error);
        return res.status(500).json({ message: "Server error!" });
    }
}

export const deleteUser = async (req, res) => {
    const { userId } = req.body;
    try {
        const user = await User.findByIdAndDelete(userId);
        if (!user) {
            return res.status(404).json({ message: "User Not Found!" });
        }
        return res.status(200).json({
            success: true,
            message: "User deleted successfully!"
        });
        
    } catch (error) {
        console.error("Error deleting user:", error);
        return res.status(500).json({ message: "Server Error" });
    }
}

export const updateUser = async (req, res) => {
    const { userId, updates } = req.body;
    
    try {
        const user = await User.findById(userId);
        
        if (!user) {
            return res.status(404).json({ message: "User not found!" });
        }
        
        if (updates.name) user.name = updates.name;
        if (updates.email) user.email = updates.email;
        if (updates.role && ['user', 'repairteam', 'admin'].includes(updates.role)) {
            user.role = updates.role;
        }
        if (updates.status && ['active', 'inactive', 'pending', 'approved', 'declined'].includes(updates.status)) {
            user.status = updates.status;
        }
        
        await user.save();
        
        return res.status(200).json({
            success: true,
            message: "User updated successfully!",
            user: {
                id: user._id,
                name: user.name,
                email: user.email,
                role: user.role,
                status: user.status
            }
        });
        
    } catch (error) {
        console.error("Error updating user:", error);
        return res.status(500).json({ message: "Server error!" });
    }
}

export const getPendingRequests = async (req, res) => {
    try {
        const pendingRequests = await User.find(
            { role: 'pending_repairteam' },
            "name email createdAt"
        ).sort({ createdAt: -1 });

        if (!pendingRequests || pendingRequests.length === 0) {
            return res.status(404).json({ message: "No pending requests found!" });
        }

        return res.status(200).json(pendingRequests);
    } catch (error) {
        console.error("Error fetching pending requests:", error);
        return res.status(500).json({ message: "Server error!" });
    }
}
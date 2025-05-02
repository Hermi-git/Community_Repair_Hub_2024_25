import express from 'express';
import {
    listUsers,
    approveRepairTeam,
    declineRepairTeam,
    deleteUser,
    updateUser,
    getPendingRequests
} from '../controllers/adminController.js';
import { adminAuth } from '../middleware/authMiddleware.js';

const router = express.Router();

router.get('/users', adminAuth, listUsers);
router.get('/pending-requests', adminAuth, getPendingRequests);
router.post('/approve-repairteam', adminAuth, approveRepairTeam);
router.post('/decline-repairteam', adminAuth, declineRepairTeam);
router.delete('/delete-user', adminAuth, deleteUser);
router.put('/update-user', adminAuth, updateUser);

export default router;
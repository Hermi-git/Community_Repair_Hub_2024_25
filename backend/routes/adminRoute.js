import express from 'express';
import {
    listUsers,
    approveRepairTeam,
    declineRepairTeam,
    deleteUser,
    updateUser,
    getPendingRequests
} from '../controllers/adminController.js';
const router = express.Router();
router.get('/users', listUsers);
router.get('/pending-requests', getPendingRequests);
router.post('/approve-repairteam', approveRepairTeam);
router.post('/decline-repairteam', declineRepairTeam);
router.delete('/delete-user', deleteUser);
router.put('/update-user', updateUser);

export default router;
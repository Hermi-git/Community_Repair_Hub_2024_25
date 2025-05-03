import express from 'express';
import upload from '../config/multerConfig.js';
import authenticateToken from '../middlewares/authMiddleware.js';
import { signup, login, logout, forgotPassword, resetPassword } from '../controllers/authController.js';

const router = express.Router();
router.post('/register', upload.single('image'), signup); 
router.post('/login', login);
router.post('/logout', authenticateToken, logout);
router.post('/forgot-password', forgotPassword);
router.post('/reset-password/:token', resetPassword);

export default router;

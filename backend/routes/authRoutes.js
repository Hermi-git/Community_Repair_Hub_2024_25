import router from 'express';
import authmiddleware from '../middleware/authMiddleware.js';
import { signup, login, logout, forgotPassword, resetPassword } from '../controllers/authController.js';

router.post('/register', signup);
router.post('/login', login);
router.post('/logout', authmiddleware, logout);
router.post('/forgot-password', forgotPassword);
router.post('/reset-password/:token', resetPassword);

export default router;
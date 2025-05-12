import express from 'express';
import { getIssues, reportIssue, searchByCategory, searchByLocation } from '../controllers/citizeController.js';
import {upload,handleMulterError} from '../config/multerConfig.js';

const router = express.Router();

router.get('/issues', getIssues);
router.post('/report', upload.single('image'), (req, res, next) => {
    console.log('File:', req.file);
    console.log('Body:', req.body);
    next();
  }, reportIssue);
router.get('/issues/category', searchByCategory);
router.get('/issues/location', searchByLocation);

export default router;

import express from 'express';
import { getIssues, reportIssue, searchByCategory, searchByLocation } from '../controllers/citizeController.js';

const router = express.Router();

router.get('/issues', getIssues);
router.post('/report', reportIssue);
router.get('/issues/category', searchByCategory);
router.get('/issues/location', searchByLocation);

export default router;

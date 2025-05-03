import express from 'express';
import upload from './multerConfig.js'; 
const router = express.Router();
router.post('/upload', upload.single('image'), (req, res) => {
  if (!req.file) return res.status(400).send('No file uploaded.');
  res.json({
    filename: req.file.filename,
    url: `/uploads/${req.file.filename}`
  });
});

export default router;

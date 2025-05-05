import { StatusCodes } from "http-status-codes";
const authenticateToken = async (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];  
  
    if (!token) {
      return res.status(StatusCodes.UNAUTHORIZED).json({ message: 'Access Token Required' });
    }
  
    jwt.verify(token, process.env.JWT_SECRET, async (err, decodedUser) => {
      if (err) {
          console.log("JWT verification error:", err);
          return res.status(StatusCodes.FORBIDDEN).json({ message: 'Invalid Token' });
      }
  
      req.user = await User.findById(decodedUser.id); 
  
      if (!req.user) {
          return res.status(StatusCodes.NOT_FOUND).json({ message: 'User not found' });
      }
  
      req.user.role = decodedUser.role;
      next();
    });
  };
export default authenticateToken
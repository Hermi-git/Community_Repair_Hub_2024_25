import nodemailer from "nodemailer";
import {
    PASSWORD_RESET_REQUEST_TEMPLATE,
    PASSWORD_RESET_SUCCESS_TEMPLATE,
} from "../utils/mailTemplate.js";
import dotenv from "dotenv";
dotenv.config();
const transporter = nodemailer.createTransport({
    host: process.env.EMAIL_HOST,
    port: 465,
    secure: true,
    auth: {
        user: process.env.EMAIL_USER,
        pass: process.env.EMAIL_PASSWORD,
    },
    tls: {
        rejectUnauthorized: false, 
    },
});

transporter.verify((error, success) => {
    if (error) {
        console.error("SMTP Configuration Error:", error);
    } else {
        console.log("SMTP Configuration Success:", success);
    }
});

const sendEmail = async ({ to, subject, htmlContent }) => {
    try {
        const mailOptions = {
            from: `"Your App Team" <${process.env.EMAIL_USER}>`,
            to,
            subject,
            html: htmlContent,
        };

        console.log("Preparing to send email with options:", mailOptions);

        await transporter.sendMail(mailOptions);
        return true;
    } catch (error) {
        console.error("Error during email sending:", error);
        return false; 
    }
};
export const sendPasswordResetEmail = async (recipientEmail, resetURL) => {
    try {
        const htmlContent = PASSWORD_RESET_REQUEST_TEMPLATE.replace("{resetURL}", resetURL);
        return await sendEmail({
            to: recipientEmail,
            subject: "Password Reset Request",
            htmlContent,
        });
    } catch (error) {
        return false;
    }
};

export const sendPasswordResetSuccessEmail = async (recipientEmail) => {
    try {
        const htmlContent = PASSWORD_RESET_SUCCESS_TEMPLATE;
        return await sendEmail({
            to: recipientEmail,
            subject: "Password Reset Successful",
            htmlContent,
        });
    } catch (error) {
        return false;
    }
};

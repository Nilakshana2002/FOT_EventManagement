function openLogin() {
    document.getElementById("loginModal").style.display = "block";
}

function closeLogin() {
    document.getElementById("loginModal").style.display = "none";
}

function openSignup() {
    document.getElementById("signupModal").style.display = "block";
}

function closeSignup() {
    document.getElementById("signupModal").style.display = "none";
}

function showSignup() {
    closeLogin();
    openSignup();
}

function showLogin() {
    closeSignup();
    openLogin();
}

function showStudentForm() {
    document.getElementById("usertype").value = "STUDENT";
    document.getElementById("btnStudent").classList.add("active");
    document.getElementById("btnStaff").classList.remove("active");
    document.getElementById("registerno").placeholder = "Student Registration No (e.g., TG1234)";
    document.getElementById("regBtn").innerText = "REGISTER AS STUDENT →";
    document.getElementById("regError").innerText = "";
}

function showStaffForm() {
    document.getElementById("usertype").value = "STAFF";
    document.getElementById("btnStaff").classList.add("active");
    document.getElementById("btnStudent").classList.remove("active");
    document.getElementById("registerno").placeholder = "Academic/Staff ID (e.g., AC1234)";
    document.getElementById("regBtn").innerText = "REGISTER AS ACADEMIC →";
    document.getElementById("regError").innerText = "";
}

function validateRegistration() {
    let userType = document.getElementById("usertype").value;
    let regNo = document.getElementById("registerno").value;
    let email = document.querySelector("#registrationForm input[name='email']").value;
    let pwd = document.getElementById("regPwd").value;
    let confirmPwd = document.getElementById("regConfirmPwd").value;
    let error = document.getElementById("regError");

    // Category-specific Registration Number Validation
    if (userType === 'STUDENT') {
        let studentPattern = /^[a-zA-Z]{2}\d{4}$/; // Standard Student Format (e.g., TG1234)
        if (!studentPattern.test(regNo)) {
            error.innerText = "❌ Invalid Student ID format! (Use e.g., TG1234)";
            return false;
        }
    } else {
        let staffPattern = /^[a-zA-Z]{2}\d{4}$/; // Assuming same length but different prefix or just ACxxxx
        if (!regNo.toUpperCase().startsWith("AC")) {
             error.innerText = "❌ Staff ID must start with 'AC'! (Use e.g., AC1234)";
             return false;
        }
    }

    // Email Validation (Minimum 2-character TLD, e.g., .com)
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
    if (!emailPattern.test(email)) {
        error.innerText = "❌ Please enter a valid Email address (e.g., user@example.com)!";
        return false;
    }

    if (pwd !== confirmPwd) {
        error.innerText = "❌ Passwords do not match!";
        return false;
    }

    error.innerText = "";
    return true;
}

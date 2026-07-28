async function login() {

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const error = document.getElementById("error");

    error.innerHTML = "";

    if (email === "" || password === "") {
        error.innerHTML = "Email and Password are required.";
        return;
    }

    try {

        const response = await post(API.LOGIN, {
            email: email,
            password: password
        });

        if (!response.ok) {
            error.innerHTML = "Invalid Email or Password.";
            return;
        }

        const data = await response.json();

        localStorage.setItem("token", data.token);

        window.location.href = "dashboard.html";

    } catch (e) {

        console.error(e);
        error.innerHTML = "Unable to connect to server.";

    }

}

function logout() {

    localStorage.clear();
    window.location.href = "index.html";

}

function checkAuthentication() {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "index.html";
    }

}

async function loadProfile() {

    try {

        const response = await get(API.PROFILE);

        if (!response.ok) {
            logout();
            return;
        }

        const profile = await response.json();

        sessionStorage.setItem("userName", profile.userName);
        sessionStorage.setItem("email", profile.email);

    } catch (e) {

        console.error(e);

    }

}

/* ---------- JWT Helpers ---------- */

function parseJwt(token) {

    if (!token) return null;

    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");

    return JSON.parse(atob(base64));

}

function getUserId() {

    const token = localStorage.getItem("token");

    if (!token) return null;

    const jwt = parseJwt(token);

    return jwt.userId;

}

function getRole() {

    const token = localStorage.getItem("token");

    if (!token) return null;

    const jwt = parseJwt(token);

    return jwt.role;

}

function getEmail() {

    const token = localStorage.getItem("token");

    if (!token) return null;

    const jwt = parseJwt(token);

    return jwt.sub;

}

function isAdmin() {

    return getRole() === "ADMIN";

}
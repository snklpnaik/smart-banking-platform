function checkAuthentication() {

    const token = localStorage.getItem("token");

    if (token == null || token === "") {
        window.location.href = "index.html";
    }

}

async function login() {

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const error = document.getElementById("error");

    error.innerHTML = "";

    if (username === "" || password === "") {
        error.innerHTML = "Username and Password are required.";
        return;
    }

    try {

        const response = await post("/auth/login", {
            username: username,
            password: password
        });

        if (response.ok) {

            const data = await response.json();

            localStorage.setItem("token", data.token);

            // Optional if your API returns these values
            if (data.username) {
                localStorage.setItem("username", data.username);
            }

            if (data.role) {
                localStorage.setItem("role", data.role);
            }

            window.location.href = "dashboard.html";

        } else {

            error.innerHTML = "Invalid username or password.";

        }

    } catch (e) {

        error.innerHTML = "Unable to connect to server.";

    }

}

function logout() {

    localStorage.clear();

    window.location.href = "index.html";

}

function isAdmin() {

    return localStorage.getItem("role") === "ADMIN";

}
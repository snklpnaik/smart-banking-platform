async function loadDashboard() {

    checkAuthentication();

    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");

    if (username) {
        document.getElementById("username").innerText = username;
    }

    if (role === "ADMIN") {
        document.getElementById("adminBtn").style.display = "inline-block";
    }

    try {

        // Update this endpoint according to your backend
        const response = await get("/account/dashboard");

        if (!response.ok) {
            alert("Unable to load dashboard.");
            return;
        }

        const data = await response.json();

        document.getElementById("balance").innerText =
            data.balance;

        document.getElementById("accountNumber").innerText =
            data.accountNumber;

        document.getElementById("accountType").innerText =
            data.accountType;

        document.getElementById("accountStatus").innerText =
            data.status;

    } catch (error) {

        console.error(error);
        alert("Server unavailable.");

    }

}
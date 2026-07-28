async function loadProfilePage() {

    checkAuthentication();

    try {

        // Ensure latest profile data
        await loadProfile();

        document.getElementById("userName").innerHTML =
            sessionStorage.getItem("userName");

        document.getElementById("email").innerHTML =
            sessionStorage.getItem("email");

        document.getElementById("role").innerHTML =
            getRole();

        document.getElementById("accountNumber").innerHTML =
            sessionStorage.getItem("accountNumber");

        document.getElementById("accountType").innerHTML =
            sessionStorage.getItem("accountType");

        document.getElementById("balance").innerHTML =
            "₹ " + parseFloat(sessionStorage.getItem("balance")).toFixed(2);

        document.getElementById("status").innerHTML =
            sessionStorage.getItem("status");

    }
    catch (e) {

        console.error(e);

        alert("Unable to load profile.");

    }

}
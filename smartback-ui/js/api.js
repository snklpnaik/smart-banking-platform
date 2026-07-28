const BASE_URL = "http://localhost:8086";

const API = {
    LOGIN: "/auth/login",
    DASHBOARD: "/account/dashboard",
    DEPOSIT: "/account/deposit",
    WITHDRAW: "/account/withdraw",
    TRANSFER: "/transaction/transfer",
    HISTORY: "/transaction/history",
    PROFILE: "/account/profile",
    ACCOUNTS: "/account/all"
};

function getToken() {
    return localStorage.getItem("token");
}

function getHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + getToken()
    };
}

async function get(endpoint) {
    const response = await fetch(BASE_URL + endpoint, {
        method: "GET",
        headers: getHeaders()
    });

    return response;
}

async function post(endpoint, data) {
    const response = await fetch(BASE_URL + endpoint, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(data)
    });

    return response;
}

async function put(endpoint, data) {
    const response = await fetch(BASE_URL + endpoint, {
        method: "PUT",
        headers: getHeaders(),
        body: JSON.stringify(data)
    });

    return response;
}

async function remove(endpoint) {
    const response = await fetch(BASE_URL + endpoint, {
        method: "DELETE",
        headers: getHeaders()
    });

    return response;
}
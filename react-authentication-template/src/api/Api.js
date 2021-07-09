import http from "../http-common";


function callCallbacks(response, callbacks) {
    // TODO
}

export async function createAccount(email, password, callbacks) {
    const response = await http.post(`/account/create?email=${email}&password=${password}`);
    return response;
}

export async function login(email, password, agentId, callbacks = {}) {
    const response = await http.post(`/auth/login?email=${email}&password=${password}&agentId=${agentId}`);
    callCallbacks(response, callbacks);

    return response;
}

export async function renewJwt(email, sut, agentId, callbacks) {
    const response = await http.post(`/auth/jwt?email=${email}&sut=${sut}&agentId=${agentId}`);
    callCallbacks(response, callbacks);

    return response;
}

export async function getPublicString() {
    const response = await http.get("/public");
    return response;
}

export async function getSecureString(jwt, callbacks) {
    const config = {
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + jwt
        }
    };
    const response = await http.get("/secure", config);
    callCallbacks(response, callbacks);

    return response;
}

export async function getAdminString(jwt) {
    const config = {
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + jwt
        }
    };
    const response = await http.get("/admin", config);
    callCallbacks(response);

    return response;
}





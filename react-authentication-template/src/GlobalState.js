import React from "react";

const initialState = {
	// "login", "createAccount", "home"
	page: "login",
	email: undefined,
	isLoggedIn: false,
	// json web token used to make authorized requests
	jwt: undefined,
	jwtValidDuration: 0,
	// single use token used to request a new json web token if the old one is expired
	sut: undefined,
};

export const globalStateContext = React.createContext();

const globalStateReducer = (state, action) => {
	console.log(action);
	switch (action.type) {
		case "setAll":
			return { ...state, ...action.payload };
		case "overrideAll":
			return { ...action.payload };
		case "setPage":
			return { ...state, page: action.payload.page };
		case "setEmail": 
			return { ...state, email: action.payload.email};
		case "setLoggedIn":
			return { ...state, isLoggedIn: action.payload.isLoggedIn };
		case "setJwt":
			return { ...state, jwt: action.payload.jwt };
		case "setSut":
			return { ...state, sut: action.payload.sut };
		case "setPage": 
			return { ...state, page: action.payload.page};
		case "resetAuth":
			return {
				page: "login",
				email: undefined,
				isLoggedIn: false,
				jwt: undefined,
				jwtValidDuration: 0,
				sut: undefined
			}
		default:
			throw new Error("Unhandled action type: " + action.type);
	}
}

export function GlobalStateProvider(props) {
	const [globalState, dispatch] = React.useReducer(globalStateReducer, initialState);

	return (
		<globalStateContext.Provider value={[globalState, dispatch]}>
			{props.children}
		</globalStateContext.Provider>
	);
}

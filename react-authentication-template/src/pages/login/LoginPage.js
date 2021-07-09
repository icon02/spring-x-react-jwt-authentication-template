import React, { useState, useContext } from "react";
import { globalStateContext } from "../../GlobalState";
import {login} from "../../api/Api";

export default function LoginPage(props) {
	const [globalState, dispatch] = useContext(globalStateContext);

	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

	function loginOnClick(e) {
		e.preventDefault();
		login(email, password, "random").then(res => dispatch({type: "setAll", payload: {jwt: res.data.jwt, sut: res.data.sut, isLoggedIn: true, email: email, jwtValidDuration: res.data.jwtValidDuration, page: "home"}}));
	}

	return (
		<div
			style={{
				width: "100vw",
				height: "100vh",
				backgroundColor: "#aabbaa",
				display: "flex",
				flexDirection: "column",
				justifyContent: "center",
				alignItems: "center",
			}}
		>
			<h2>Login</h2>
			<form
				style={{
					border: "1px solid black",
					borderRadius: 8,
					padding: 16,
				}}
			>
				Email:
				<br />
				<input
					value={email}
					onChange={(e) => setEmail(e.target.value)}
					type="text"
				/>
				<br />
				<br />
				Password:
				<br />
				<input
					value={password}
					onChange={(e) => setPassword(e.target.value)}
					type="password"
				/>
				<br />
				<br />
				<button onClick={loginOnClick}>Login</button>
			</form>
		</div>
	);
}

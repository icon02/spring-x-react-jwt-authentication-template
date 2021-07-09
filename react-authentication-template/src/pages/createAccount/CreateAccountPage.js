import React, { useState, useContext } from "react";
import {createAccount} from "../../api/Api";
import { globalStateContext } from "../../GlobalState";

export default function CreateAccountPage(props) {
	const [globalState, dispatch] = useContext(globalStateContext);

	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");


	function createAccountOnClick(e) {
		e.preventDefault();
		createAccount(email, password).then(res => {
			if(res.status === 200) alert("Success");
			else alert("Error!")

			console.log(res);
		});
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
			<h2>Create Account</h2>
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
					type="text"
				/>
				<br />
				<br />
				<button onClick={createAccountOnClick}>Create</button>
			</form>
		</div>
	);
}

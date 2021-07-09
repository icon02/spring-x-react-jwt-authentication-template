import React, { useState, useContext } from "react";
import {getPublicString, getSecureString, getAdminString} from "../../api/Api";
import UserApi from "../../api/UserApi";

import { globalStateContext } from "../../GlobalState";

const userApi = new UserApi();

export default function HomePage(props) {
	const [globalState, _] = useContext(globalStateContext);

	const [publicString, setPublicString] = useState("");
	const [secureString, setSecureString] = useState("");
	const [adminString, setAdminString] = useState("");

	function onGetPublicStringClick() {
		getPublicString().then(res => setPublicString(res.data.value));
	}

	function onGetSecureStringClick() {
		getSecureString(globalState.jwt).then(res => setSecureString(res.data.value));
	}

	function onGetAdminStringClick() {
		getAdminString(globalState.jwt).then(res => setAdminString(res.data.value));
	}

	return (
		<div
			style={{
				backgroundColor: "#fafbfc",
				width: "100vw",
				height: "100vh",
				display: "flex",
				flexDirection: "column",
				justifyContent: "center",
				alignItems: "center",
			}}
		>
			<h2>Home</h2>

			<div
				style={{
					border: "1px solid black",
					borderRadius: 8,
					padding: 16,
					width: 300,
					textAlign: "center",
				}}
			>
				<button onClick={onGetPublicStringClick}>Get public string</button>
				<br />
				{'Public string: "' + publicString + '"'}
				<br />
				<br />
				<button onClick={onGetSecureStringClick}>Get secure string</button>
				<br />
				{'Secure string: "' + secureString + '"'}
				<br />
				<br />
				<button onClick={onGetAdminStringClick}>Get admin string</button>
				<br />
				{'Admin string: "' + adminString + '"'}
			</div>
		</div>
	);
}

import React, { useContext } from "react";
import LoginPage from "./pages/login/LoginPage";
import "./App.css";
import HomePage from "./pages/home/HomePage";
import CreateAccountPage from "./pages/createAccount/CreateAccountPage";
import ClockTimerComponent from "./components/ClockTimerComponent";
import { globalStateContext } from "./GlobalState";
import Navbar from "./components/Navbar";


function createTimeState(millis) {
	let jwtValidHours = 0;
	let jwtValidMinutes = 0;
	let jwtValidSeconds = 0;
	let restTime = millis;
	jwtValidHours = Math.floor(restTime / 1000 / 60 / 60);
	restTime = restTime - jwtValidHours * 1000 * 60 * 60;
	jwtValidMinutes = Math.floor(restTime / 1000 / 60);
	restTime = restTime - jwtValidMinutes * 1000 * 60;
	jwtValidSeconds = Math.floor(restTime / 1000);

	return {hours: jwtValidHours, minutes: jwtValidMinutes, seconds: jwtValidSeconds};
}

function App() {
	const [globalState, dispatch] = useContext(globalStateContext);

	return (
		<div id="app-container">
			<Navbar />
			<p
				style={{
					position: "absolute",
					bottom: 0,
					left: 0,
					overflow: "hidden",
					margin: 8
				}}
			>
				Logged in: {globalState.isLoggedIn ? "true" : "false"}
				<br />
				Email: {globalState.email || "undefined" }
				<br />
				JWT: {globalState.jwt || "undefined"}
				<br />
				SUT: {globalState.sut || "undefined"}
				<br />
				New JWT in: <ClockTimerComponent startTime={createTimeState(globalState.jwtValidDuration)} onZero={() => dispatch({type: "setPage", payload: {page: "login"}})}  />
			</p>
			{globalState.page === "login" && <LoginPage />}
			{globalState.page === "home" && <HomePage />}
			{globalState.page === "createAccount" && <CreateAccountPage />}
		</div>
	);
}

export default App;

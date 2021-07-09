import React, {useContext} from "react";
import {globalStateContext} from "../GlobalState";

const map = {
    "login": {
        btnText: "Create Account",
        newPageState: "createAccount"
    },
    "createAccount": {
        btnText: "Login",
        newPageState: "login"
    },
    "home": {
        btnText: "Logout",
        newPageState: "login"
    }
}

export default function Navbar(props) {
    const [globalState, dispatch] = useContext(globalStateContext);


    function onButtonClick(_) {
        if(globalState.page === "home") dispatch({type: "resetAuth"});
        else dispatch({type: "setPage", payload: {page: map[globalState.page].newPageState}})
    }

    return (
        <nav 
        style={{
            position: "absolute",
            top: 0, 
            left: 0,
            right: 0,
            height: 60,
            backgroundColor: "rgba(78, 198, 90, 0.5)"
        }}>
            <button 
                onClick={onButtonClick}
                style={{
                    backgroundColor: "lightgreen",
                    outline: "none",
                    border: "none",
                    padding: 8,
                    margin: 8
                }}
            >
                {map[globalState.page].btnText}
            </button>
        </nav>
    );
}
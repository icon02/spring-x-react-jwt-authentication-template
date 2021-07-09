import React, {useState, useEffect} from "react";

export default function ClockTimerComponent({startTime, belowZero = false, onZero = () => {}}) {
    var hours;
    var minutes;
    var seconds;

    const [time, setTime] = useState({hours: hours, minutes: minutes, seconds: seconds});

    function initTime() {
        hours = startTime && startTime.hours ? startTime.hours : 0;
        minutes = startTime && startTime.minutes ? startTime.minutes : 0;
        seconds = startTime && startTime.seconds ? startTime.seconds : 0;
    };
    initTime();

    var decrementInterval = null;

    function decrement() {
        console.log("decrement");
        let transfer = false;
        if(seconds <= 0 && minutes <= 0 && hours <= 0) {
            onZero();
            if(!belowZero) {
                clearInterval(decrementInterval);
                decrementInterval = null;
                return;
            }
        }

        seconds -= 1;
        if(seconds === -1) {
            seconds = 59;
            transfer = true;
        }

        if(transfer) {
            minutes -= 1;
            if(minutes === -1) {
                minutes = 59;
                transfer = true;
            } else {
                transfer = false;
            }
        }

        if(transfer) {
            hours -= 1;
        }

        setTime({hours: hours, minutes: minutes, seconds: seconds});
    }

    useEffect(() => {
        clearInterval(decrementInterval);
        decrementInterval = null;
        initTime();
        setTime({hours, minutes, seconds});

        if(belowZero || (seconds > 0 || minutes > 0 || hours > 0)) 
            decrementInterval = setInterval(decrement, 1000);
        return () => {
            if(decrementInterval !== null) {
                clearInterval(decrementInterval);
                decrementInterval = null;
            }
        }
    }, [startTime]);

    return <>{time.hours < 10 && "0"}{time.hours}:{time.minutes < 10 && "0"}{time.minutes}:{time.seconds < 10 && "0"}{time.seconds}</>
}
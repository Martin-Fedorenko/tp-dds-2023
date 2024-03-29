import { signOut } from "https://www.gstatic.com/firebasejs/10.2.0/firebase-auth.js"
import { auth } from "./firebase.js";


const logout = document.querySelector("#logout");

if(logout){
    logout.addEventListener("click", async (e) => {
      e.preventDefault();
      try {
        await signOut(auth)
        console.log("signup out");
      } catch (error) {
        console.log(error)
      }
    });
}

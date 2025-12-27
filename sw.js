// handle the beforeinstallprompt event 
window.addEventListener('beforeinstallprompt', e => {
  // prevent the install dialog from appearing too early
  e.preventDefault();

  // store the event for later use
  window.deferredPrompt = e;
});

// event listener for the install button click
installButton.addEventListener('click', () => {
  if(window.deferredPrompt) {
    // call the prompt method on the deferredPrompt object to display the install dialog
    window.deferredPrompt.prompt();
  }
  else {
    // show a dialog with instructions for browsers that don't support beforeinstallprompt
  }
}
      
Documentation

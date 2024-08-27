import { FormControl } from "@angular/forms";

const noSpaceAllowed = (control: FormControl) => {
    if (control.value != null && control.value.indexOf(' ') != -1) {
        return { noSpaceAllowed: true }
    }

    return null;
}
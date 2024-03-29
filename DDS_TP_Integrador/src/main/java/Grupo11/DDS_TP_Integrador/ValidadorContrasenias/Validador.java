package Grupo11.DDS_TP_Integrador.ValidadorContrasenias;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validador {
    private static final String PASSWORD_REGEX =
            "^(?=.*\\d)(?!.* {2})(?=.*[a-z])(?=.*[A-Z]).{8,64}$";
    //de 8 a 64 caracteres, sin espacios consecutivos, al menos una minuscula, una mayuscula y un digito

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    //valida si password aparece entre las 10000 peores contraseñas
    public boolean validate_weak_password(String password)
    {
        try {
            File file = new File("src\\main\\top-10000-passwords.txt");
            Scanner file_reader = new Scanner(file);
            while (file_reader.hasNextLine()) {
                String weak_password = file_reader.nextLine();
                if(password.equalsIgnoreCase(weak_password))
                    return false;
            }
            file_reader.close();
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    //valida si password cumple con la estructura especificada
    public boolean validate_password_regex(String password)
    {
        return (PASSWORD_PATTERN.matcher(password).matches());
    }

    public boolean validate_password(String password){

        if(validate_password_regex(password) && validate_weak_password(password)) {
            return true;
        }else{
            return false;
        }
    }

}

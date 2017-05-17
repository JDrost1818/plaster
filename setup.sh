#!/bin/sh

uninstall_plaster() {
    echo "Uninstalling current installation of plaster"
    sudo rm /usr/bin/plaster;
    sudo rm -rf /usr/share/plaster;
}

install_plaster() {
    echo "Building project"
    mvn clean install -DskipTests=true -q;

    echo "Installing plaster"
    sudo mkdir /usr/share/plaster;
    sudo mkdir /usr/share/plaster/lib;

    plaster_file_name=""
    cd target/;
    for file_name in plaster*.jar
    do
        plaster_file_name=${file_name};
    done

    sudo cp ${plaster_file_name} /usr/share/plaster/;
    sudo cp lib/* /usr/share/plaster/lib/;

    sudo touch /usr/bin/plaster;
    sudo chmod 777 /usr/bin/plaster;
    sudo chmod 777 /usr/share/plaster;
    sudo chmod 777 /usr/share/plaster/${plaster_file_name};

    echo "#!/bin/sh" >> /usr/bin/plaster
    echo "" >> /usr/bin/plaster
    echo "java -jar /usr/share/plaster/${plaster_file_name} \$@" >> /usr/bin/plaster

    cd ..

    echo "Successfully installed plaster"
}
reinstall="first_install"
if test -e /usr/bin/plaster
then
    read -p "Plaster already installed, would you like to reinstall (y/n) " reinstall
fi

case ${reinstall} in
    [Yy]* ) uninstall_plaster; break;;
    first_install ) break;;
    * ) exit;;
esac

install_plaster
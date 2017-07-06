#!/bin/sh

uninstall_plaster() {
    bin_root="$1"
    share_root="$2"

    echo "Uninstalling current installation of plaster"
    sudo rm ${bin_root}/plaster;
    sudo rm -rf ${share_root}/plaster;
}

install_plaster() {
    bin_root="$1"
    share_root="$2"

    echo "Building project"
    mvn clean install -DskipTests=true -q;

    echo "Installing plaster"
    sudo mkdir ${share_root}/plaster;
    sudo mkdir ${share_root}/plaster/lib;

    plaster_file_name=""
    cd target/;
    for file_name in plaster*.jar
    do
        plaster_file_name=${file_name};
    done

    sudo cp ${plaster_file_name} ${share_root}/plaster/;
    sudo cp lib/* ${share_root}/plaster/lib/;

    sudo touch ${bin_root}/plaster;
    sudo chmod 777 ${bin_root}/plaster;
    sudo chmod 777 ${share_root}/plaster;
    sudo chmod 777 ${share_root}/plaster/${plaster_file_name};

    echo "#!/bin/sh" >> ${bin_root}/plaster
    echo "" >> ${bin_root}/plaster
    echo "java -jar ${share_root}/plaster/${plaster_file_name} \$@" >> ${bin_root}/plaster

    cd ..

    echo "Successfully installed plaster"
}

bin_root=""
share_root=""

device="$(uname -s)"
case ${device} in
    Darwin ) bin_root="/usr/local/bin"; share_root="/usr/local/share"; break;;
    Linux ) bin_root="/usr/bin"; share_root="/usr/share"; break;;
    * ) echo "Unsupported command line tool"; exit;;
esac

reinstall="first_install"
if test -e ${bin_root}/plaster
then
    read -p "Plaster already installed, would you like to reinstall (y/n) " reinstall
fi

case ${reinstall} in
    [Yy]* ) uninstall_plaster ${bin_root} ${share_root}; break;;
    first_install ) break;;
    * ) exit;;
esac

install_plaster ${bin_root} ${share_root}
#!/bin/sh

uninstall_plaster() {
    bin_location="$1"
    share_location="$2"

    echo "Uninstalling current installation of plaster"
    sudo rm ${bin_location}/plaster;
    sudo rm -rf ${share_location}/plaster;
}

install_plaster() {
    bin_location="$1"
    share_location="$2"

    echo "Building project"
    mvn clean install -DskipTests=true -q;

    echo "Installing plaster"
    sudo mkdir ${share_location}/plaster;
    sudo mkdir ${share_location}/plaster/lib;

    plaster_file_name=""
    cd target/;
    for file_name in plaster*.jar
    do
        plaster_file_name=${file_name};
    done

    sudo cp ${plaster_file_name} ${share_location}/plaster/;
    sudo cp lib/* ${share_location}/plaster/lib/;

    sudo touch ${bin_location}/plaster;
    sudo chmod 777 ${bin_location}/plaster;
    sudo chmod 777 ${share_location}/plaster;
    sudo chmod 777 ${share_location}/plaster/${plaster_file_name};

    echo "#!/bin/sh" >> ${bin_location}/plaster
    echo "" >> ${bin_location}/plaster
    echo "java -jar ${share_location}/plaster/${plaster_file_name} \$@" >> ${bin_location}/plaster

    cd ..

    echo "Successfully installed plaster"
}

bin_location=""
share_location=""

device="$(uname -s)"
if [ "$device" == "Darwin" ]
then
    echo "Mac OS detected"
    bin_location="/usr/local/bin"
    share_location="/usr/local/share"
elif [ "$device" == "Linux" ]
then
    echo "Linux OS detected"
    bin_location="/usr/bin"
    share_location="/usr/share"
else
    echo "Unsupported command line tool"
    exit
fi

reinstall="first_install"
if test -e ${bin_location}/plaster
then
    read -p "Plaster already installed, would you like to reinstall (y/n) " reinstall
fi

case ${reinstall} in
    [Yy]* ) uninstall_plaster ${bin_location} ${share_location}; break;;
    first_install ) break;;
    * ) exit;;
esac

install_plaster ${bin_location} ${share_location}
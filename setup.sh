#!/bin/sh

uninstall_plaster() {
    bin_file="$1"
    share_root_dir="$2"

    echo "Uninstalling current installation of plaster"
    sudo rm ${bin_file};
    sudo rm -rf ${share_root_dir};
}

install_plaster() {
    bin_file="$1"
    share_root_dir="$2"

    echo "Building project"
    mvn clean install -DskipTests=true -q;

    echo "Installing plaster"
    sudo mkdir ${share_root_dir};
    sudo mkdir ${share_root_dir}/lib;

    plaster_file_name=""
    cd target/;
    for file_name in plaster*.jar
    do
        plaster_file_name=${file_name};
    done

    sudo cp ${plaster_file_name} ${share_root_dir}/;
    sudo cp lib/* ${share_root_dir}/lib/;

    sudo touch ${bin_file};
    sudo chmod 777 ${bin_file};
    sudo chmod 777 ${share_root_dir};
    sudo chmod 777 ${share_root_dir}/${plaster_file_name};

    echo "#!/bin/sh" >> ${bin_file}
    echo "" >> ${bin_file}
    echo "java -jar ${share_root_dir}/${plaster_file_name} \$@" >> ${bin_file}

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

bin_file=${bin_root}/plaster
share_root_dir=${share_root}/plaster

reinstall="first_install"
if test -e ${bin_file}
then
    read -p "Plaster already installed, would you like to reinstall (y/n) " reinstall
fi

case ${reinstall} in
    [Yy]* ) uninstall_plaster ${bin_file} ${share_root_dir}; break;;
    first_install ) break;;
    * ) exit;;
esac

install_plaster ${bin_file} ${share_root_dir}
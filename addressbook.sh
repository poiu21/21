#!/bin/bash

ADDRESS_BOOK="address_book.txt"

# Function to display the menu
display_menu() {
    echo "======== Address Book Menu ========"
    echo "1. Create Address Book"
    echo "2. View Address Book"
    echo "3. Insert a Record"
    echo "4. Delete a Record"
    echo "5. Modify a Record"
    echo "6. Exit"
    echo "===================================="
}

# Function to create a new address book
create_address_book() {
    > $ADDRESS_BOOK
    echo "Address book created: $ADDRESS_BOOK"
}

# Function to view the address book
view_address_book() {
    if [[ -f $ADDRESS_BOOK ]]; then
        if [[ -s $ADDRESS_BOOK ]]; then
            echo "======== Address Book Records ========"
            cat -n $ADDRESS_BOOK
        else
            echo "Address book is empty."
        fi
    else
        echo "No address book found. Please create one first."
    fi
}

# Function to insert a new record
insert_record() {
    read -p "Enter Name: " name
    read -p "Enter Phone: " phone
    read -p "Enter Email: " email
    echo "$name | $phone | $email" >> $ADDRESS_BOOK
    echo "Record inserted successfully."
}

# Function to delete a record
delete_record() {
    view_address_book
    read -p "Enter the line number of the record to delete: " line
    sed -i "${line}d" $ADDRESS_BOOK
    echo "Record deleted successfully."
}

# Function to modify a record
modify_record() {
    view_address_book
    read -p "Enter the line number of the record to modify: " line
    read -p "Enter New Name: " name
    read -p "Enter New Phone: " phone
    read -p "Enter New Email: " email
    sed -i "${line}s/.*/$name | $phone | $email/" $ADDRESS_BOOK
    echo "Record modified successfully."
}

# Main program loop
while true; do
    display_menu
    read -p "Enter your choice: " choice
    case $choice in
        1) create_address_book ;;
        2) view_address_book ;;
        3) insert_record ;;
        4) delete_record ;;
        5) modify_record ;;
        6) echo "Exiting..."; break ;;
        *) echo "Invalid choice. Please try again." ;;
    esac
done
#chmod +x address_book.sh
#./address_book.sh

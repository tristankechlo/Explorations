import os

def delete_all_zips():
    # get current directory
    current_dir = os.path.dirname(__file__)
    # get a list off all folders in the current directory
    dirContents = os.listdir(current_dir)
    # loop through all folders
    for folder in dirContents:
        # skip everything that is not a zip file
        if not folder.endswith('.zip'):
            continue
        # delete the zip file
        os.remove(folder)
        print(f'Deleted datapack {folder}')

if __name__ == '__main__':
    delete_all_zips()
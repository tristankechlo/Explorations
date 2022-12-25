import shutil
import os

# this scripts will make all folders in the current directory into zip files


def make_datapacks():
    # get current directory
    current_dir = os.path.dirname(__file__)
    # get a list off all folders in the current directory
    dirContents = os.listdir(current_dir)
    # loop through all folders
    for folder in dirContents:
        # skip everything that is not a folder
        if not os.path.isdir(folder):
            continue
        # put all contents of the folder in a zip file
        shutil.make_archive(folder, 'zip', folder)
        print(f'Created datapack {folder}.zip')


if __name__ == '__main__':
    make_datapacks()

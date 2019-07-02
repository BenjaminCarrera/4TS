/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { CategoriaSkillDeleteDialogComponent } from 'app/entities/categoria-skill/categoria-skill-delete-dialog.component';
import { CategoriaSkillService } from 'app/entities/categoria-skill/categoria-skill.service';

describe('Component Tests', () => {
  describe('CategoriaSkill Management Delete Component', () => {
    let comp: CategoriaSkillDeleteDialogComponent;
    let fixture: ComponentFixture<CategoriaSkillDeleteDialogComponent>;
    let service: CategoriaSkillService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [CategoriaSkillDeleteDialogComponent]
      })
        .overrideTemplate(CategoriaSkillDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaSkillDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaSkillService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
